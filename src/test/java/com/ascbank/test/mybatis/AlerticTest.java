/**
 *
 */
package com.ascbank.test.mybatis;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ascbank.dao.ArticleMapper;
import com.ascbank.model.Article;

/**
 * @author jie
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AlerticTest {
	private static Log	logger	= LogFactory.getLog(AlerticTest.class);
	
	@Autowired
	ApplicationContext	context;
	
	// @Test
	public void articleAddTest() {
		final ArticleMapper am = (ArticleMapper) context.getBean("articleMapper");
		
		final Article record = new Article();
		
		record.setContext(
				"鸡蛋是公认的营养价值较高的食品，其富含优质蛋白质和其他营养素，这其中土鸡蛋也就是散养鸡产的鸡蛋更是受到广大消费者的青睐，然而，随着饲养环境的污染，散养鸡蛋中竟然被检出了二噁英，二噁英相信大家之前曾经听到过，那么二噁英到底是什么，都有什么危害，有哪些来源呢？事件缘由2016年6月21日，中外三家民间环保组织共同发布《中国热点地区鸡蛋中的持久性有机污染物》报告。参与这项研究的三家机构，分别是中国的源头爱好者环境研究所、捷克共和国的阿尼卡（Arnika）协会，以及总部位于瑞典的国际消除持久性有机污染物联盟。研究开展于2013年至2015年期间，采集国内6个典型工业污染源附近的10组散养鸡蛋，并设置了1组采购自北京一家超市产自大型养鸡场的鸡蛋样品作为对照。报告指出，在所调查的的散养鸡蛋样品中，检出了超过欧盟标准限值的二噁英等持久性有机污染。6个采样地点所处地区都有污染“可疑区”，分别是广西北海的冶金厂、齐齐哈尔的聚氯乙烯厂、四川资阳的多氯联苯电容器历史使用和贮存场地，以及广州李坑、深圳、武汉的垃圾焚烧厂。11组鸡蛋被送去实验室检测，结果发现，散养鸡蛋样品的二噁英含量全都超过欧盟规定的用于筛查问题食品的限值。超标最严重的采样点在广西北海的一家冶金厂和武汉两家互相紧邻的废弃物焚烧厂附近。报告的作者、阿尼卡协会主席提到，其中得到进一步检测的5组鸡蛋样本，二噁英毒性当量超过欧盟标准限值1.3-4.9倍。而作为对照样品的北京超市鸡蛋，则显著低于欧盟的控制标准。这说明就持久性有机污染物而言，饲养环境和方式对鸡蛋的质量有很大影响。散养鸡蛋由于和土壤接触紧密，所以是土壤或灰尘中二噁英污染的敏感指示物，所以该研究报告表明，我国一些地区土壤的二恶英污染已经非常严重。");
		// record.setId(6L);
		record.setMenuId(2L);
		record.setUploadtime(new Date());
		logger.info("=======================insert Before record = " + record + "=========================");
		
		logger.info("=======================return = " + am.insertSelective(record) + "=========================");
		
		logger.info("=======================insert After record = " + record + "=========================");
		
	}
	
	// @Test
	public void articleDeleteTest() {
		final ArticleMapper am = (ArticleMapper) context.getBean("articleMapper");
		final int record = am.deleteByPrimaryKey(1L);
		
		logger.info("=======================" + record + "=========================");
		
	}
	
	@Test
	public void articleQueryTest() {
		final ArticleMapper am = (ArticleMapper) context.getBean("articleMapper");
		final Article record = am.selectByPrimaryKey(4L);
		
		logger.info("=======================" + record + "=========================");
		logger.info("=======================" + record + "=========================");
	}
	
	@Before
	public void setUp() {
		// Create
		
	}
	
	@After
	public void tearDown() {
		
	}
}
