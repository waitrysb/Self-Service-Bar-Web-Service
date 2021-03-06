package com.SelfServiceBarWeb.mapper;

import com.SelfServiceBarWeb.model.Pad;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

public interface PadMapper {
    @Select("SELECT * FROM pad_info WHERE bar_id=#{bar_id}")
    List<Pad> getAll(@Param("bar_id") String bar_id);

    @Select("SELECT * FROM pad_info WHERE id = #{id}")
    Pad getByPadId(@Param("id") String id);

    @Select("SELECT * FROM pad_info WHERE ip_address = #{ip_address}")
    Pad getByPadIpAddress(@Param("ip_address") String ip_address);

    @Insert("INSERT INTO pad_info(ip_address, seat_id,producer,create_at,use_at,bar_id)" +
            " VALUES(#{ip_address}, #{seat_id}, #{producer}, #{create_at}, #{use_at}, #{bar_id});")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = String.class)
    void createNewPad(Pad pad);
}
