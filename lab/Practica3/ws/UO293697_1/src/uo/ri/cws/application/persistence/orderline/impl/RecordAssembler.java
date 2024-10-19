package uo.ri.cws.application.persistence.orderline.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.orderline.OrderLineGateway.OrderLineRecord;

public class RecordAssembler {

    public static Optional<OrderLineRecord> toOrderlineRecord(ResultSet m)
            throws SQLException {
        
        return m.next()
            ? Optional.of( resultSetToOrderlineRecord(m) )
            : Optional.ofNullable(null);
    }

    public static List<OrderLineRecord> toOrderLineRecordList(ResultSet rs)
            throws SQLException {
        List<OrderLineRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add( resultSetToOrderlineRecord(rs) );
        }
        return res;
    }

    private static OrderLineRecord resultSetToOrderlineRecord(ResultSet rs)
            throws SQLException {
       OrderLineRecord od = new OrderLineRecord();
        od.price=rs.getDouble("price");
        od.quantity=rs.getInt("quantity");
        od.sparePartId = rs.getString("sparepart_id");
        od.orderId=rs.getString("order_id");
        return od;
    }

}
