package uo.ri.cws.application.persistence.provider.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class ProviderGatewayImpl implements ProviderGateway {

    @Override
    public void add(ProviderRecord t) throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TPROVIDERS_ADD"));
            try {
                pst.setString(1, t.id);
                pst.setString(2, t.email);
                pst.setString(3, t.name);
                pst.setString(4, t.nif);
                pst.setString(5, t.phone);
                pst.setLong(6, t.version);

                pst.execute();
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void remove(String id) throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TPROVIDERS_DELETE"));
            try {
                pst.setString(1, id);

                pst.execute();
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void update(ProviderRecord t) throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TPROVIDERS_UPDATE"));
            try {
                pst.setString(1, t.name);
                pst.setString(2, t.email);
                pst.setString(3, t.nif);
                pst.setString(4, t.phone);
                pst.setString(5, t.id);

                pst.execute();
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public Optional<ProviderRecord> findById(String id)
        throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TPROVIDERS_FINDBYID"));
            try {
                pst.setString(1, id);

                st = pst.executeQuery();

                return RecordAssembler.toProviderRecord(st);
            } finally {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ProviderRecord> findAll() throws PersistenceException {
        return null;
    }

    @Override
    public Optional<ProviderRecord> findByNif(String nif) {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TPROVIDERS_FINDBYNIF"));
            try {
                pst.setString(1, nif);

                st = pst.executeQuery();

                return RecordAssembler.toProviderRecord(st);

            } finally {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ProviderRecord> findByName(String name) {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TPROVIDERS_FINDBYNAME"));
            try {
                pst.setString(1, name);

                st = pst.executeQuery();

                return RecordAssembler.toProviderRecordList(st);

            } finally {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<ProviderRecord> findEqualsFields(ProviderRecord record) {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c
                .prepareStatement(Queries.get("TPROVIDERS_FINDEQUALSFIELDS"));
            try {
                pst.setString(1, record.name);
                pst.setString(2, record.email);
                pst.setString(3, record.phone);

                st = pst.executeQuery();

                return RecordAssembler.toProviderRecord(st);

            } finally {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
