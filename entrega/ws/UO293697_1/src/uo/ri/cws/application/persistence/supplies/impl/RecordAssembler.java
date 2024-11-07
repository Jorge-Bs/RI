package uo.ri.cws.application.persistence.supplies.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.supplies.SuppliesGateway.SuppliesRecord;

public class RecordAssembler {

    public static Optional<SuppliesRecord> toSuppliesRecord(ResultSet m)
            throws SQLException {
        
        return m.next()
            ? Optional.of( resultSetToSuppliesRecord(m) )
            : Optional.ofNullable(null);
    }

    public static List<SuppliesRecord> toSuppliesRecordList(ResultSet rs)
            throws SQLException {
        List<SuppliesRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add( resultSetToSuppliesRecord(rs) );
        }
        return res;
    }

    private static SuppliesRecord resultSetToSuppliesRecord(ResultSet rs)
            throws SQLException {
        SuppliesRecord rec = new SuppliesRecord();
        rec.id=rs.getString("id");
        rec.price=rs.getDouble("price");
        rec.version=rs.getLong("version");
        rec.deliveryTerm=rs.getInt("deliveryterm");
        rec.providerId=rs.getString("provider_id");
        rec.sparepartId=rs.getString("sparepart_id");
        
        return rec;
    }

}
