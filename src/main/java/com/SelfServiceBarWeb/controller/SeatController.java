package com.SelfServiceBarWeb.controller;


import com.SelfServiceBarWeb.model.Seat;
import com.SelfServiceBarWeb.model.request.ChangeSeatRequest;
import com.SelfServiceBarWeb.model.request.CreateSeatRequest;
import com.SelfServiceBarWeb.model.request.SeatStateEnum;
import com.SelfServiceBarWeb.model.request.TokenTypeEnum;
import com.SelfServiceBarWeb.service.SeatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Muki on 2018/11/4
 */

@RequestMapping(path = "/seats")
@RestController
@EnableAutoConfiguration
@Api(tags = "Seat", description = "座位相关操作")
public class SeatController {
    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    //该请求是管理员调用
    @ApiOperation(value = "获取指定id的座位信息")
    @RequestMapping(path = "/{seatId}", method = RequestMethod.GET)
    public Seat getSeatInfo(@PathVariable(value = "seatId") String seatId, @RequestParam(value = "token") String token, @RequestParam(value = "tokenType") TokenTypeEnum tokenTypeEnum) throws Exception {
        return seatService.getBySeatId(seatId, token, tokenTypeEnum);
    }

    @ApiOperation(value = "获取所有桌椅信息")
    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Seat> getAllTable(@RequestParam(value = "token") String token) throws Exception {
        return seatService.getAllSeats(token);
    }

    @ApiOperation(value = "添加新设备")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Seat createNewSeat(@RequestBody CreateSeatRequest createSeatRequest) throws Exception {
        return seatService.createNewSeat(createSeatRequest);
    }

    //todo  暂时不知道座位的控制方式，在更改状态时需要传递的参数
    //该请求是管理员调用
    @ApiOperation(value = "更改座位的状态")
    @RequestMapping(path = "/{seatId}", method = RequestMethod.PATCH)
    public Seat changeSeatState(@PathVariable(value = "seatId") String seatId, @RequestBody ChangeSeatRequest changeSeatRequest) throws Exception {
        return seatService.changeSeatState(seatId, changeSeatRequest);
    }

    //该请求是管理员调用
    @ApiOperation(value = "更改座位的状态")
    @RequestMapping(path = "/all", method = RequestMethod.PATCH)
    public boolean changeAllSeatState(@RequestParam(value = "token") String token, @RequestParam(value = "mode") SeatStateEnum seatStateEnum) throws Exception {
        return seatService.changeAllSeatState(token, seatStateEnum);
    }

    //由用户调用
    @ApiOperation(value = "检查座位是否干净(用户)")
    @RequestMapping(path = "/cleaning", method = RequestMethod.GET)
    public Boolean getCleaning(@RequestParam(value = "orderNo") String orderNo) throws Exception {
        throw new UnsupportedOperationException();
    }
}
