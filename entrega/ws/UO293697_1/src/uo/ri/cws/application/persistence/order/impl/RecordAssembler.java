package uo.ri.cws.application.persistence.order.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;

public class RecordAssembler {

    public static Optional<OrderRecord> toOrderRecord(ResultSet m)
            throws SQLException {
        
        return m.next()
            ? Optional.of( resultSetToOrderRecord(m) )
            : Optional.ofNullable(null);
    }

    public static List<OrderRecord> toOrderRecordList(ResultSet rs)
            throws SQLException {
        List<OrderRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add( resultSetToOrderRecord(rs) );
        }
        return res;
    }

    private static OrderRecord resultSetToOrderRecord(ResultSet rs)
            throws SQLException {
        OrderRecord value = new OrderRecord();
        value.id=rs.getString("id");
        value.amount=rs.getDouble("amount");
        value.code=rs.getString("code");
        value.orderDate=rs.getDate("ORDEREDDATE").toLocalDate();
        if(rs.getDate("receptiondate")!=null) {
            value.receptionDate=rs.getDate("receptiondate").toLocalDate();
        }
        value.state=rs.getString("state");
        value.version=rs.getLong("version");
        value.providerId=rs.getString("PROVIDER_ID");
        return value;
    }

}
