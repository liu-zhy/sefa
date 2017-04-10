package cn.sefa.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import cn.sefa.ast.IEnvironment;
import cn.sefa.ast.NestedEnv;

/**
 * @author Lionel
 *
 */
public class SefaUtil {

	public static void CopyEnv(IEnvironment from , IEnvironment to){
	
		Set<Entry<String, Object>> f = from.getTable().entrySet();
		HashMap<String,Object> t = to.getTable();
		for(Entry<String , Object> e: f){
			t.put(e.getKey(), e.getValue());
		}
		to.setOuter(from.getOuter());
		
	}
	
	@Test
	public void test(){
		NestedEnv from = new NestedEnv();
		NestedEnv to = new NestedEnv();
		HashMap<String , Object> ft = from.getTable();
//		HashMap<String , Object> tt = to.getTable();
		ft.put("12", "12//");
		ft.put("23", "23//");
		ft.put("34", "34//");
		ft.put("45", "45//");
		ft.put("56", "56//");
		
		CopyEnv(from, to);
		for(Entry<String, Object> e:to.getTable().entrySet()){
			System.out.println(e.getKey()+":"+e.getValue());
		}
		
	}
	
}
