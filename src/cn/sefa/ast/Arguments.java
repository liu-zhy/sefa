package cn.sefa.ast;

import java.util.List;

import cn.sefa.exception.SefaException;
import cn.sefa.symbol.Code;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.vm.Opcode;

/**
 * @author Lionel
 *
 */
public class Arguments extends Postfix {

	public Arguments(List<ASTree> list) {
		super(list);
	}

	public int numOfArgs(){
		return numOfChildren() ;
	}
	
	public List<ASTree> getArgs(){
		return children;
	}

	@Override
	public Object eval(IEnvironment callerEnv, Object val) {
		
		if(val instanceof NativeFunction){
			return evalNativeFunc(callerEnv,(NativeFunction)val);
		}
		
		else if(!(val instanceof Function)){
			throw new SefaException(val+" is not a function",this);
		}
		
		Function func = (Function)val;
		ParameterList params = func.getParams();
		
		if(numOfArgs() != params.numOfChildren()){
			throw new SefaException("the numbers of argument don't match .",this);
		}
		IEnvironment newEnv = func.makeEnv();
		int num = 0 ;
		for(ASTree t: getArgs()){
			params.eval(newEnv,num++,t.eval(callerEnv));
		}
		
		return func.getBody().eval(newEnv);
	}
	
	public Object evalNativeFunc(IEnvironment env, NativeFunction func){
		
		if(numOfArgs() != func.numOfParams()){
			throw new SefaException("the number of arguments is incorrect. ",this) ;
		}
		
		Object[] args = new Object[func.numOfParams()];
		int cnt = 0;
		for(ASTree t : getArgs()){
			args[cnt++] = t.eval(env);
		}
		
		return func.invoke(args, this) ;
	}
	
	@Override
	public void compile(Code c){
		
		int newOffset = c.frameSize;
		int numOfArgs = 0 ;
		for (ASTree t : this){
			t.compile(c);
			c.add(Opcode.MOVE);
			c.add(Opcode.encodeRegister(--c.nextReg));
			c.add(Opcode.encodeByteOffset(newOffset++));
			numOfArgs++ ;
		}
		c.add(Opcode.CALL);
		c.add(Opcode.encodeRegister(--c.nextReg));
		c.add(Opcode.encodeByteOffset(numOfArgs));
		c.add(Opcode.MOVE);
		c.add(Opcode.encodeByteOffset(c.frameSize));
		c.add(Opcode.encodeRegister(c.nextReg++));
		
	}
	
}