package cn.sefa.symbol;

import cn.sefa.exception.SefaException;

/**
 * @author Lionel
 *
 */
public class SymbolThis extends Symbols {
	
	public static final String NAME = "this";
	public SymbolThis(Symbols outer){
		super(outer);
		add(NAME);
	}
	
	@Override
	public int putNew(String key){
		throw new SefaException("fatal") ;
	}
	@Override
	public Location put(String key){
		Location loc = outer.put(key);
		if(loc.nest >= 0)
			loc.nest++ ;
		return loc;
	}

}
