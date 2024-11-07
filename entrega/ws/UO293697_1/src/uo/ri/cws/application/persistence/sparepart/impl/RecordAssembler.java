package uo.ri.cws.application.persistence.sparepart.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;

public class RecordAssembler {

    public static Optional<SparePartRecord> toSparePartRecord(ResultSet m)
            throws SQLException {
        
        return m.next()
            ? Optional.of( resultSetToSparePartRecord(m) )
            : Optional.ofNullable(null);
    }

    public static List<SparePartRecord> toSparePartRecordList(ResultSet rs)
            throws SQLException {
        List<SparePartRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add( resultSetToSparePartRecord(rs) );
        }
        return res;
    }

    private static SparePartRecord resultSetToSparePartRecord(ResultSet rs)
            throws SQLException {
        SparePartRecord rec = new SparePartRecord();
        rec.code=rs.getString("code");
        rec.id=rs.getString("id");
        rec.description=rs.getString("description");
        rec.maxStock=rs.getInt("maxstock");
        rec.minStock=rs.getInt("minstock");
        rec.price=rs.getDouble("price");
        rec.stock=rs.getInt("stock");
        rec.version=rs.getLong("version");
        
        return rec;
    }

}
