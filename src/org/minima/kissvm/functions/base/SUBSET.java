package org.minima.kissvm.functions.base;

import org.minima.kissvm.Contract;
import org.minima.kissvm.exceptions.ExecutionException;
import org.minima.kissvm.functions.MinimaFunction;
import org.minima.kissvm.values.HEXValue;
import org.minima.kissvm.values.Value;

/**
 * Works on Scripts and HEX
 * @author spartacusrex
 *
 */
public class SUBSET extends MinimaFunction {

	public SUBSET() {
		super("SUBSET");
	}

	@Override
	public Value runFunction(Contract zContract) throws ExecutionException {
		checkExactParamNumber(3);
		
		//Get a a subset of a hex value..
		int start = zContract.getNumberParam(0, this).getNumber().getAsInt();
		int end   = zContract.getNumberParam(1, this).getNumber().getAsInt();
		int len   = end - start;
		if(len<0) {
			throw new ExecutionException("Negative SUBSET length "+len);
		}
		
		//Now pick it out of the 3rd value..
		byte[] orig = zContract.getHEXParam(2, this).getRawData();
		
		//Now get the subset
		byte[] subs = new byte[len];
		System.arraycopy(orig, start, subs, 0, len);
		
		return new HEXValue(subs);	
	}
	
	@Override
	public MinimaFunction getNewFunction() {
		return new SUBSET();
	}
}