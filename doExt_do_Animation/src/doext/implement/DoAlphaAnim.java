package doext.implement;

import org.json.JSONObject;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import core.helper.DoJsonHelper;
import core.helper.DoTextHelper;

public class DoAlphaAnim extends DoAnim{
	private float alphaFrom;
	private float alphaTo;
	public float getAlphaFrom() {
		return alphaFrom;
	}
	public void setAlphaFrom(float alphaFrom) {
		this.alphaFrom = alphaFrom;
	}
	public float getAlphaTo() {
		return alphaTo;
	}
	public void setAlphaTo(float alphaTo) {
		this.alphaTo = alphaTo;
	}
	public void setValuesFromJson(JSONObject data) throws Exception{
		String value = DoJsonHelper.getString(data, "alphaFrom", null);
		if(value != null){
			this.setAlphaFrom(DoTextHelper.strToFloat(value, 0));
		}
	    value = DoJsonHelper.getString(data, "alphaTo", null);
		if(value != null){
			this.setAlphaTo(DoTextHelper.strToFloat(value, 0));
		}
		super.setValuesFromJson(data);
	}
	@Override
	public Animation createAnimation(double xzoom, double yzoom){
		Animation animation = new AlphaAnimation(this.getAlphaFrom(), this.getAlphaTo());
		dealAnimation(animation);
		return animation;
	}
}