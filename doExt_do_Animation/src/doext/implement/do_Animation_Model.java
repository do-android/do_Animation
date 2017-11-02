package doext.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import core.DoServiceContainer;
import core.helper.DoJsonHelper;
import core.helper.DoTextHelper;
import core.interfaces.DoIScriptEngine;
import core.interfaces.DoIUIModuleView;
import core.interfaces.datamodel.DoIAnimation;
import core.object.DoInvokeResult;
import doext.define.do_Animation_IMethod;
import doext.define.do_Animation_MAbstract;

/**
 * 自定义扩展MM组件Model实现，继承do_Animation_MAbstract抽象类，并实现do_Animation_IMethod接口方法；
 * #如何调用组件自定义事件？可以通过如下方法触发事件：
 * this.model.getEventCenter().fireEvent(_messageName, jsonResult);
 * 参数解释：@_messageName字符串事件名称，@jsonResult传递事件参数对象； 获取DoInvokeResult对象方式new
 * DoInvokeResult(this.getUniqueKey());
 */
public class do_Animation_Model extends do_Animation_MAbstract implements do_Animation_IMethod, DoIAnimation {
	private DoAnim doAnimSet;
	private List<DoAnim> doAnims;
	private Map<String, DoAnim> doAnimMap;

	public do_Animation_Model() throws Exception {
		super();
		doAnimSet = new DoAnim();
		doAnims = new ArrayList<DoAnim>();
		doAnimMap = new HashMap<String, DoAnim>();
	}

	/**
	 * 同步方法，JS脚本调用该组件对象方法时会被调用，可以根据_methodName调用相应的接口实现方法；
	 * 
	 * @_methodName 方法名称
	 * @_dictParas 参数（K,V），获取参数值使用API提供DoJsonHelper类；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public boolean invokeSyncMethod(String _methodName, JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		// ...do something
		if ("alpha".equals(_methodName)) {
			this.alpha(_dictParas, _scriptEngine, _invokeResult);
			return true;
		} else if ("transfer".equals(_methodName)) {
			this.transfer(_dictParas, _scriptEngine, _invokeResult);
			return true;
		} else if ("scale".equals(_methodName)) {
			this.scale(_dictParas, _scriptEngine, _invokeResult);
			return true;
		} else if ("rotate".equals(_methodName)) {
			this.rotate(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		return super.invokeSyncMethod(_methodName, _dictParas, _scriptEngine, _invokeResult);
	}

	/**
	 * 异步方法（通常都处理些耗时操作，避免UI线程阻塞），JS脚本调用该组件对象方法时会被调用， 可以根据_methodName调用相应的接口实现方法；
	 * 
	 * @_methodName 方法名称
	 * @_dictParas 参数（K,V），获取参数值使用API提供DoJsonHelper类；
	 * @_scriptEngine 当前page JS上下文环境
	 * @_callbackFuncName 回调函数名 #如何执行异步方法回调？可以通过如下方法：
	 *                    _scriptEngine.callback(_callbackFuncName,
	 *                    _invokeResult);
	 *                    参数解释：@_callbackFuncName回调函数名，@_invokeResult传递回调函数参数对象；
	 *                    获取DoInvokeResult对象方式new
	 *                    DoInvokeResult(this.getUniqueKey());
	 */
	@Override
	public boolean invokeAsyncMethod(String _methodName, JSONObject _dictParas, DoIScriptEngine _scriptEngine, String _callbackFuncName) throws Exception {
		// ...do something
		return super.invokeAsyncMethod(_methodName, _dictParas, _scriptEngine, _callbackFuncName);
	}

	@Override
	public void loadSync(String _content) throws Exception {
		super.loadSync(_content);
		loadModel(_content);
	}

	@Override
	public void load(String _content) throws Exception {
		super.load(_content);
		loadModel(_content);
	}

	private void loadModel(String _content) throws Exception {
		JSONObject _rootJsonNode = DoJsonHelper.loadDataFromText(_content);
		doAnimSet.setValuesFromJson(_rootJsonNode);
		JSONArray alphaNodes = DoJsonHelper.getJSONArray(_rootJsonNode, "alpha");
		if (alphaNodes != null) {
			for (int i = 0; i < alphaNodes.length(); i++) {
				JSONObject alphaNode = alphaNodes.getJSONObject(i);
				if (alphaNode != null) {
					alpha(DoJsonHelper.getJSONObject(alphaNode, "data"), DoJsonHelper.getString(alphaNode, "id", null));
				}
			}
		}
		JSONArray transferNodes = DoJsonHelper.getJSONArray(_rootJsonNode, "transfer");
		if (transferNodes != null) {
			for (int i = 0; i < transferNodes.length(); i++) {
				JSONObject transferNode = transferNodes.getJSONObject(i);
				if (transferNode != null) {
					transfer(DoJsonHelper.getJSONObject(transferNode, "data"), DoJsonHelper.getString(transferNode, "id", null));
				}
			}
		}
		JSONArray scaleNodes = DoJsonHelper.getJSONArray(_rootJsonNode, "scale");
		if (scaleNodes != null) {
			for (int i = 0; i < scaleNodes.length(); i++) {
				JSONObject scaleNode = scaleNodes.getJSONObject(i);
				if (scaleNode != null) {
					scale(DoJsonHelper.getJSONObject(scaleNode, "data"), DoJsonHelper.getString(scaleNode, "id", null));
				}
			}
		}
		JSONArray rotateNodes = DoJsonHelper.getJSONArray(_rootJsonNode, "rotate");
		if (rotateNodes != null) {
			for (int i = 0; i < rotateNodes.length(); i++) {
				JSONObject rotateNode = rotateNodes.getJSONObject(i);
				if (rotateNode != null) {
					rotate(DoJsonHelper.getJSONObject(rotateNode, "data"), DoJsonHelper.getString(rotateNode, "id", null));
				}
			}
		}
	}

	@Override
	public void dispose() {
		if (doAnims != null) {
			doAnims.clear();
			doAnims = null;
		}
		if (doAnimMap != null) {
			doAnimMap.clear();
			doAnimMap = null;
		}
		super.dispose();
	}

	/**
	 * 透明度动画；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void alpha(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		JSONObject data = DoJsonHelper.getJSONObject(_dictParas, "data");
		String id = DoJsonHelper.getString(_dictParas, "id", null);
		alpha(data, id);
	}

	/**
	 * 旋转动画；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void rotate(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		JSONObject data = DoJsonHelper.getJSONObject(_dictParas, "data");
		String id = DoJsonHelper.getString(_dictParas, "id", null);
		rotate(data, id);
	}

	/**
	 * 缩放动画；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void scale(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		JSONObject data = DoJsonHelper.getJSONObject(_dictParas, "data");
		String id = DoJsonHelper.getString(_dictParas, "id", null);
		scale(data, id);
	}

	/**
	 * 位移动画；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void transfer(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		JSONObject data = DoJsonHelper.getJSONObject(_dictParas, "data");
		String id = DoJsonHelper.getString(_dictParas, "id", null);
		transfer(data, id);

	}

	@Override
	public void setViewAnimation(final View view, final DoIScriptEngine _scriptEngine, final DoInvokeResult _invokeResult, final String _callbackFuncName) throws Exception {
		if (doAnims != null && doAnims.size() > 0) {
			final AnimationSet set = new AnimationSet(false);
			set.setFillAfter(DoTextHelper.strToBool(getPropertyValue("fillAfter"), false));
			set.setFillEnabled(true);
			DoIUIModuleView moduleView = (DoIUIModuleView) view;
			double xzoom = moduleView.getModel().getXZoom();
			double yzoom = moduleView.getModel().getYZoom();
			for (DoAnim doAnim : doAnims) {
				Animation animation = doAnim.createAnimation(xzoom, yzoom);
				set.addAnimation(animation);
			}
			set.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					_scriptEngine.callback(_callbackFuncName, _invokeResult);
				}
			});
			DoServiceContainer.getPageViewFactory().getAppContext().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					view.startAnimation(set);
					view.invalidate();
				}
			});

		}

	}

	private void alpha(JSONObject data, String id) throws Exception {
		if (data != null) {
			DoAlphaAnim anim = null;
			if (id != null) {
				anim = (DoAlphaAnim) doAnimMap.get(id);
			}
			if (anim == null) {
				anim = new DoAlphaAnim();
				doAnims.add(anim);
				if (id != null) {
					doAnimMap.put(id, anim);
				}
			}
			anim.setId(id);
			anim.setValuesFromJson(data);
		}
	}

	private void transfer(JSONObject data, String id) throws Exception {
		if (data != null) {
			DoTransferAnim anim = null;
			if (id != null) {
				anim = (DoTransferAnim) doAnimMap.get(id);
			}
			if (anim == null) {
				anim = new DoTransferAnim();
				doAnims.add(anim);
				if (id != null) {
					doAnimMap.put(id, anim);
				}
			}
			anim.setId(id);
			anim.setValuesFromJson(data);
		}
	}

	private void scale(JSONObject data, String id) throws Exception {
		if (data != null) {
			DoScaleAnim anim = null;
			if (id != null) {
				anim = (DoScaleAnim) doAnimMap.get(id);
			}
			if (anim == null) {
				anim = new DoScaleAnim();
				doAnims.add(anim);
				if (id != null) {
					doAnimMap.put(id, anim);
				}
			}
			anim.setId(id);
			anim.setValuesFromJson(data);
		}
	}

	private void rotate(JSONObject data, String id) throws Exception {
		if (data != null) {
			DoRotateAnim anim = null;
			if (id != null) {
				anim = (DoRotateAnim) doAnimMap.get(id);
			}
			if (anim == null) {
				anim = new DoRotateAnim();
				doAnims.add(anim);
				if (id != null) {
					doAnimMap.put(id, anim);
				}
			}
			anim.setId(id);
			anim.setValuesFromJson(data);
		}
	}
}