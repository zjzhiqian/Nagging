package com.hzq.common.exception;

/**
 * @author hzq
 *
 * 2015年8月28日 下午11:03:26 
 */
public class CustomException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	//异常信息
		private String message;
		
		public CustomException(String message){
			super(message);
			this.message = message;
			
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
	
}
