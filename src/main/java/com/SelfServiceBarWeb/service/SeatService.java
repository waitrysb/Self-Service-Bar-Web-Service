package com.SelfServiceBarWeb.service;

import com.SelfServiceBarWeb.constant.ResponseMessage;
import com.SelfServiceBarWeb.mapper.HardwareLogMapper;
import com.SelfServiceBarWeb.mapper.HardwareStateMapper;
import com.SelfServiceBarWeb.mapper.SeatMapper;
import com.SelfServiceBarWeb.mapper.TableMapper;
import com.SelfServiceBarWeb.model.*;

import com.SelfServiceBarWeb.model.request.ChangeSeatRequest;
import com.SelfServiceBarWeb.model.request.CreateSeatRequest;
import com.SelfServiceBarWeb.model.request.SeatStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muki on 2018/11/4
 */

@Service
public class SeatService {
    private final SeatMapper seatMapper;
    private final TableMapper tableMapper;
    private final AdministratorService administratorService;
    private final HardwareStateMapper hardwareStateMapper;
    private final HardwareLogMapper hardwareLogMapper;

    @Autowired
    public SeatService(SeatMapper seatMapper, TableMapper tableMapper, AdministratorService administratorService, HardwareStateMapper hardwareStateMapper, HardwareLogMapper hardwareLogMapper) {
        this.seatMapper = seatMapper;
        this.tableMapper = tableMapper;
        this.administratorService = administratorService;
        this.hardwareStateMapper = hardwareStateMapper;
        this.hardwareLogMapper = hardwareLogMapper;
    }

    private List<HardwareLog> getHardwareLog(String id) {
        List<HardwareLog> hardwareLogs = hardwareLogMapper.getAllByIdAndType(id, HardwareTypeEnum.seat.getValue());
        List<HardwareLog> logs = new ArrayList<>();

        for (int i = hardwareLogs.size() - 1; i >= 0 && i >= hardwareLogs.size() - 10; i--) {
            logs.add(hardwareLogs.get(i));
        }
        return logs;
    }

    public List<Seat> getAllSeats(String token) throws Exception {
        administratorService.getAdministratorIdFromToken(token);

        List<Seat> seats = seatMapper.getAllSeats();
        for (Seat seat : seats) {
            Hardware monitorState = hardwareStateMapper.getByIdAndType(seat.getId(), HardwareTypeEnum.seat.getValue());
            seat.setState(HardwareStateEnum.getHardwareStateEnum(monitorState.getState()));
            seat.setHardwareLogs(getHardwareLog(seat.getId()));
        }
        return seats;
    }

    public List<Seat> getByTableId(String tableId) throws Exception {
        List<Seat> seats = seatMapper.getByTableId(tableId);
        for (Seat seat : seats) {
            Hardware monitorState = hardwareStateMapper.getByIdAndType(seat.getId(), HardwareTypeEnum.seat.getValue());
            seat.setState(HardwareStateEnum.getHardwareStateEnum(monitorState.getState()));
            seat.setHardwareLogs(getHardwareLog(seat.getId()));
        }
        return seats;
    }

    public Seat getBySeatId(String seatId, String token) throws Exception {
        //验证token，用户或者管理员
        administratorService.getAdministratorIdFromToken(token);

        Seat seat = seatMapper.getBySeatId(seatId);
        if (seat == null)
            throw new SelfServiceBarWebException(400, ResponseMessage.ERROR, ResponseMessage.GET_SEAT_INFO_ERROR);

        Hardware monitorState = hardwareStateMapper.getByIdAndType(seat.getId(), HardwareTypeEnum.seat.getValue());
        seat.setState(HardwareStateEnum.getHardwareStateEnum(monitorState.getState()));
        seat.setHardwareLogs(getHardwareLog(seat.getId()));

        return seat;
    }

    public Seat createNewSeat(CreateSeatRequest createSeatRequest) throws Exception {
        administratorService.getAdministratorIdFromToken(createSeatRequest.getLoginToken());

        Table table = tableMapper.getTableById(createSeatRequest.getTable_id());
        if (table == null)
            throw new SelfServiceBarWebException(400, ResponseMessage.ERROR, ResponseMessage.CREATE_SEAT_ERROR);

        Seat seat = new Seat();
        seat.setHardwareId(createSeatRequest.getHardwareId());
        seat.setIpAddress(createSeatRequest.getIpAddress());
        seat.setTable_id(createSeatRequest.getTable_id());
        seat.setPosition_x(createSeatRequest.getPosition_x());
        seat.setPosition_y(createSeatRequest.getPosition_y());
        seat.setLocation(createSeatRequest.getLocation());

        seatMapper.createNewSeat(seat);
        Hardware hardware = new Hardware(seat.getId(), HardwareTypeEnum.seat.getValue());
        hardwareStateMapper.createNewHardwareState(hardware);
        seat.setState(HardwareStateEnum.close);

        //加入日志
        HardwareLog hardwareLog = new HardwareLog(seat.getId(), HardwareTypeEnum.seat.getValue(), "administer", HardwareStateEnum.create.getValue(), "");
        hardwareLogMapper.createNewLog(hardwareLog);

        seat.setHardwareLogs(getHardwareLog(seat.getId()));

        return seat;
    }

    public Seat changeSeatState(String seatId, ChangeSeatRequest changeSeatRequest) throws Exception {
        administratorService.getAdministratorIdFromToken(changeSeatRequest.getToken());

        Seat seat = seatMapper.getBySeatId(seatId);
        HardwareLog hardwareLog;
        if (seat == null)
            throw new SelfServiceBarWebException(400, ResponseMessage.ERROR, ResponseMessage.GET_SEAT_INFO_ERROR);
        //修改seat状态
        switch (changeSeatRequest.getMode()) {
            case open:
                hardwareStateMapper.openByIdAndType(seat.getId(), HardwareTypeEnum.seat.getValue());
                //加入日志
                hardwareLog = new HardwareLog(seat.getId(), HardwareTypeEnum.seat.getValue(), "administer", HardwareStateEnum.open.getValue(), "");
                hardwareLogMapper.createNewLog(hardwareLog);
                break;
            case close:
                hardwareStateMapper.closeByIdAndType(seat.getId(), HardwareTypeEnum.seat.getValue());
                //加入日志
                hardwareLog = new HardwareLog(seat.getId(), HardwareTypeEnum.seat.getValue(), "administer", HardwareStateEnum.close.getValue(), "");
                hardwareLogMapper.createNewLog(hardwareLog);
                break;
        }
        seat.setHardwareLogs(getHardwareLog(seat.getId()));

        return seat;
    }
}
