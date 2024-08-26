import com.mysql.cj.jdbc.MysqlXADataSource;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class XATransactionExample {
    public static void main(String[] args) {
        // 配置第一个数据源
        MysqlXADataSource xaDataSource1 = new MysqlXADataSource();
        xaDataSource1.setUrl("jdbc:mysql://localhost:3306/db1");
        xaDataSource1.setUser("root");
        xaDataSource1.setPassword("password");

        // 配置第二个数据源
        MysqlXADataSource xaDataSource2 = new MysqlXADataSource();
        xaDataSource2.setUrl("jdbc:mysql://localhost:3306/db2");
        xaDataSource2.setUser("root");
        xaDataSource2.setPassword("password");

        XAConnection xaConn1 = null;
        XAConnection xaConn2 = null;
        Connection conn1 = null;
        Connection conn2 = null;
        Xid xid1 = null;
        Xid xid2 = null;
        try {
            // 获取XA连接和底层数据库连接
            xaConn1 = xaDataSource1.getXAConnection();
            xaConn2 = xaDataSource2.getXAConnection();
            conn1 = xaConn1.getConnection();
            conn2 = xaConn2.getConnection();

            // 获取XA资源
            XAResource xaRes1 = xaConn1.getXAResource();
            XAResource xaRes2 = xaConn2.getXAResource();

            // 创建XID (事务ID)
            xid1 = createXid(1, 1);
            xid2 = createXid(2, 2);

            // 开始XA事务
            xaRes1.start(xid1, XAResource.TMNOFLAGS);
            xaRes2.start(xid2, XAResource.TMNOFLAGS);

            // 执行SQL操作
            PreparedStatement ps1 = conn1.prepareStatement("INSERT INTO table1 (name) VALUES (?)");
            ps1.setString(1, "John");
            ps1.executeUpdate();

            PreparedStatement ps2 = conn2.prepareStatement("INSERT INTO table2 (name) VALUES (?)");
            ps2.setString(1, "Doe");
            ps2.executeUpdate();

            // 结束XA事务
            xaRes1.end(xid1, XAResource.TMSUCCESS);
            xaRes2.end(xid2, XAResource.TMSUCCESS);

            // 准备XA事务
            int prp1 = xaRes1.prepare(xid1);
            int prp2 = xaRes2.prepare(xid2);

            // 提交或回滚XA事务
            if (prp1 == XAResource.XA_OK && prp2 == XAResource.XA_OK) {
                xaRes1.commit(xid1, false);
                xaRes2.commit(xid2, false);
                System.out.println("Transaction committed successfully.");
            } else {
                xaRes1.rollback(xid1);
                xaRes2.rollback(xid2);
                System.out.println("Transaction rolled back.");
            }

        } catch (SQLException | XAException e) {
            e.printStackTrace();
            try {
                if (xaConn1 != null) {
                    xaConn1.getXAResource().rollback(xid1);
                }
                if (xaConn2 != null) {
                    xaConn2.getXAResource().rollback(xid2);
                }
            } catch (XAException | SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn1 != null) {
                    conn1.close();
                }
                if (conn2 != null) {
                    conn2.close();
                }
                if (xaConn1 != null) {
                    xaConn1.close();
                }
                if (xaConn2 != null) {
                    xaConn2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Xid createXid(int id1, int id2) {
        return new Xid() {
            @Override
            public int getFormatId() {
                return 12345;
            }

            @Override
            public byte[] getGlobalTransactionId() {
                return new byte[] { (byte) id1 };
            }

            @Override
            public byte[] getBranchQualifier() {
                return new byte[] { (byte) id2 };
            }
        };
    }
}
