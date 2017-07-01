package hello;

import java.util.Map;

import org.pentaho.di.core.EngineMetaInterface;
import org.pentaho.di.core.annotations.Inject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.trans.TransMeta;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.paraview.etl.kettle.spoon.utils.Spoon;

@Controller
@RequestMapping("/kettles")
public class Application {
	
	@Bean(name = "spoon")
	public static Spoon getSpoon() throws KettleException {
		Spoon staticSpoon = Spoon.getInstance();
		
		return staticSpoon;
	}
	
	@Inject()
	private Spoon spoon;
	
	@RequestMapping("/get/stepinfo")
	@ResponseBody
	public Map<ObjectId, Map<Integer, Map<String, String>>> getStepInfo(String transName, String stepName) {
		try {
			return spoon.getStepMetaAttribute(transName, stepName);
			
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping("/get/steplist")
	@ResponseBody
	String[] getStepList(String transName) {
		return spoon.getStepNames(transName);
	}
	
	@RequestMapping("/get/stepxml")
	@ResponseBody
	public String getStepxml(String transName, String stepName) {
		try {
			return spoon.getStepMetaXML(transName, stepName);
			
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping("/get/translist")
	@ResponseBody
	public String[] getTransList() {
		
		try {
			EngineMetaInterface emi = spoon.openFile("D:/Workspaces/Kettle/kettle-src/test/demo.ktr", true);
			System.out.println(emi.getName());
			TransMeta tm = (TransMeta) emi;
			System.out.println(tm.hasMissingPlugins());
			
			return spoon.getLoadedTransNames();
			
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	@RequestMapping("/set/stepinfo")
	@ResponseBody
	public Map<ObjectId, Map<Integer, Map<String, String>>> setStepInfo(String transName, String stepName, Map<ObjectId, Map<Integer, Map<String, String>>> map) {
		spoon.setStepMetaAttribute(transName, stepName, map);
		
		try {
			spoon.saveTransformation(transName, "D:/Workspaces/Kettle/kettle-src/test/demo.ktr");
			return spoon.getStepMetaAttribute(transName, stepName);
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping("/set/stepxml")
	@ResponseBody
	public Map<ObjectId, Map<Integer, Map<String, String>>> setStepxml(String transName, String stepName, String xml) {
		
		try {
			spoon.setStepMetaXML(transName, stepName, xml);
			spoon.saveTransformation(transName, "D:/Workspaces/Kettle/kettle-src/test/demo.ktr");
			return spoon.getStepMetaAttribute(transName, stepName);
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
}
