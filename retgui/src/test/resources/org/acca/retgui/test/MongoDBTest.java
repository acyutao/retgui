/**
 * 
 */
package org.acca.retgui.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yutao
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
public class MongoDBTest {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void test(){
		City city = new City();
//		city.setId("2");
		city.setCityCode("PEK3");
		city.setCityName("北京");
		
		mongoTemplate.insert(city, "RET");
		Query query = new Query(Criteria.where("cityCode").is("PEK3"));
		
		City a = mongoTemplate.findOne(query, City.class, "RET");
		
		System.out.println(a);
		
//		mongoTemplate.
	}
}
