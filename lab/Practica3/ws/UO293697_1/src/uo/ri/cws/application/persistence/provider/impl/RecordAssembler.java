package uo.ri.cws.application.persistence.provider.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

public class RecordAssembler {

    public static Optional<ProviderRecord> toProviderRecord(ResultSet m)
            throws SQLException {
        
        return m.next()
            ? Optional.of( resultSetToProviderRecord(m) )
            : Optional.ofNullable(null);
    }

    public static List<ProviderRecord> toProviderRecordList(ResultSet rs)
            throws SQLException {
        List<ProviderRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add( resultSetToProviderRecord(rs) );
        }
        return res;
    }

    private static ProviderRecord resultSetToProviderRecord(ResultSet rs)
            throws SQLException {
        ProviderRecord value = new ProviderRecord();
        value.id=rs.getString("id");
        value.nif=rs.getString("nif");
        value.version=rs.getLong("version");
        value.name=rs.getString("name");
        value.email=rs.getString("email");
        value.phone=rs.getString("phone");
        return value;
    }

}
