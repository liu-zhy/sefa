package cn.sefa.ast;

import java.util.List;

import cn.sefa.exception.SefaException;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.vm.Code;
import cn.sefa.vm.Opcode;
import cn.sefa.vm.SefaVM;
import cn.sefa.vm.VMFunction;

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
			return callNativeFunction(callerEnv, val);
		}
		else if(!(val instanceof VMFunction)){
			throw new SefaException("function call find error.");
		}
		VMFunction func = (VMFunction) val;
		ParameterList params = func.getParams();
		if(numOfArgs() != params.numOfParams()){
			throw new SefaException("the arguments are incompatible.");
		}
		int num = 0 ;
		for(ASTree t : this){
			params.eval(callerEnv,num++,t.eval(callerEnv));
		}
		SefaVM svm = callerEnv.sefaVM();
		svm.run(func.entry());
		return svm.getStack()[0];
		
	}

	private Object callNativeFunction(IEnvironment callerEnv, Object val) {
		Object[] args = new Object[numOfArgs()] ;
		int i = 0 ;
		for(ASTree t : this){
			args[i++] = t.eval(callerEnv);
		}
		return ((NativeFunction) val).invoke(args , this) ;
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