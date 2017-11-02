package doext.define;

import core.object.DoMultitonModule;
import core.object.DoProperty;
import core.object.DoProperty.PropertyDataType;


public abstract class do_Animation_MAbstract extends DoMultitonModule{

	protected do_Animation_MAbstract() throws Exception {
		super();
	}
	
	/**
	 * 初始化
	 */
	@Override
	public void onInit() throws Exception{
        super.onInit();
        //注册属性
		this.registProperty(new DoProperty("fillAfter", PropertyDataType.Bool, "true", false));
	}
}