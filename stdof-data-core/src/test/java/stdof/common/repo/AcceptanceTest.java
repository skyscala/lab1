/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.common.repo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import java.io.File;

import java.nio.file.Files;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import static junit.framework.Assert.assertTrue;
import org.bson.types.ObjectId;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import stdof.DbConfig;

import stdof.common.docs.entity.StdofEntity;
import stdof.common.opr.StdofManageOpr;
import stdof.common.opr.StdofQueryOpr;
import stdof.lang.CommonLogger;
import stdof.lang.ExternalPropUtil;
import stdof.lang.JsonUtil;
import stdof.query.FilterCriteria;
import stdof.query.SortCriteria;
import stdof.query.StringFilterCriteria;

/**
 *
 * @author zeyarhtike
 */
public class AcceptanceTest {

    private static MongodExecutable mongodExe;
    private static MongodProcess mongod;

    private static final String database = "database";

    private static final boolean embeddedDbFlag = true;

    @BeforeClass
    public static void before() {

        if (embeddedDbFlag) {
            try {

                ExternalPropUtil.mergeProp("db.name", "test-db");
                ExternalPropUtil.mergeProp("db.url", "mongodb://localhost:27000/test-db");
                CommonLogger.log(AcceptanceTest.class, "start db..");
                MongodStarter starter = MongodStarter.getDefaultInstance();
                Storage replication = new Storage(database, null, 0);
                mongodExe = starter.prepare(new MongodConfigBuilder()
                        .version(Version.Main.PRODUCTION).replication(replication)
                        .net(new Net("localhost", 27000, Network.localhostIsIPv6()))
                        .build());
                mongod = mongodExe.start();

            } catch (Exception ex) {
                try {
                    if (mongod != null) {
                        mongod.stop();
                    }
                } catch (Exception e) {
                    //ingore
                }
                try {
                    if (mongodExe != null) {
                        mongodExe.stop();
                    }
                } catch (Exception e) {
                    //ignore
                }

            }
        }
    }

    @AfterClass
    public static void after() {
        if (embeddedDbFlag) {
            try {
                if (mongod != null) {
                    mongod.stop();
                }
            } catch (Exception e) {
                //ingore
            }
            try {
                if (mongodExe != null) {
                    mongodExe.stop();
                }
            } catch (Exception e) {
                //ignore
            }
            deleteFile(new File(database));
        }

    }

    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFile(f);
            }
        }
        try {
            Files.delete(file.toPath());
        } catch (Exception ex) {
            CommonLogger.log(AcceptanceTest.class, "File delete error.", ex);
        }
    }

    @Test
    public void test() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DbConfig.class)) {

            StdofEntity entity = new StdofEntity();
            StdofManageOpr saveOpr = StdofManageOprBeanFactory.create(context);
            StdofQueryOpr queryOpr = context.getAutowireCapableBeanFactory().createBean(StdofPersistenceAdapter.class);

            saveOpr.save(entity);

            boolean isEq = entity.getId() != null;
            assertTrue(isEq);

            String id = entity.getId().toHexString();

            boolean isNull = entity.getAttributes() == null;
            assertTrue(isNull);

            Map<String, Object> attributes = new HashMap<>();

            attributes.put("attr1", "attr1");
            attributes.put("attr2", "attr2");
            entity.setAttributes(attributes);
            saveOpr.save(entity);

            for (int i = 0; i < 9; i++) {
                Map<String, Object> attrs1 = createAttr("attr1", "attr1");
                Map<String, Object> attrs2 = createAttr("attr2", "attr2");
                attrs2.putAll(attrs1);
                attrs2.put("createdDate", Calendar.getInstance().getTime());
                StdofEntity e = saveOpr.save(createEntity(attrs2));
                CommonLogger.log(AcceptanceTest.class, JsonUtil.toJsonString(e));
            }

            StringFilterCriteria textFilter1 = new StringFilterCriteria();
            textFilter1.setKey("attr1");
            textFilter1.setValue("attr1");
            textFilter1.setOperation("equalsIgnoreCase");
            StringFilterCriteria textFilter2 = new StringFilterCriteria();
            textFilter2.setKey("attr2");
            textFilter2.setValue("attr2");
            textFilter2.setOperation("equalsIgnoreCase");

            SortCriteria sortCriteria = new SortCriteria();
            sortCriteria.setKey("attr1");
            sortCriteria.setType("DESC");

            Page<StdofEntity> page = queryOpr.query(0, 5, "and", new FilterCriteria[]{textFilter1, textFilter2}, new SortCriteria[]{sortCriteria});

            CommonLogger.log(AcceptanceTest.class, "totalPages: " + page.getTotalPages());

            assertTrue(page.getTotalPages() == 2);
            assertTrue(page.getTotalElements() == 10);

            StdofEntity deletedEntity = saveOpr.delete(new ObjectId(id));

            assertTrue(deletedEntity.getId().equals(entity.getId()));
            assertTrue(deletedEntity.getAttributes().get("attr1").equals(attributes.get("attr1")));

        }

    }

    private Map<String, Object> createAttr(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private StdofEntity createEntity(Map<String, Object> attrs) {
        StdofEntity entity = new StdofEntity();
        entity.setAttributes(attrs);
        return entity;
    }
}
