package doext.implement;

import org.json.JSONObject;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import core.helper.DoJsonHelper;
import core.helper.DoTextHelper;

public class DoTransferAnim extends DoAnim {
	private float fromX;
	private float fromY;
	private float toX;
	private float toY;

	public float getFromX() {
		return fromX;
	}

	public void setFromX(float fromX) {
		this.fromX = fromX;
	}

	public float getFromY() {
		return fromY ;
	}

	public void setFromY(float fromY) {
		this.fromY = fromY;
	}

	public float getToX() {
		return toX;
	}

	public void setToX(float toX) {
		this.toX = toX;
	}

	public float getToY() {
		return toY;
	}

	public void setToY(float toY) {
		this.toY = toY;
	}

	public void setValuesFromJson(JSONObject data) throws Exception {
		String value = DoJsonHelper.getString(data, "fromX", null);
		if (value != null) {
			this.setFromX(DoTextHelper.strToFloat(value, 0));
		}
		value = DoJsonHelper.getString(data, "fromY", null);
		if (value != null) {
			this.setFromY(DoTextHelper.strToFloat(value, 0));
		}
		value = DoJsonHelper.getString(data, "toX", null);
		if (value != null) {
			this.setToX(DoTextHelper.strToFloat(value, 0));
		}
		value = DoJsonHelper.getString(data, "toY", null);
		if (value != null) {
			this.setToY(DoTextHelper.strToFloat(value, 0));
		}
		super.setValuesFromJson(data);
	}

	@Override
	public Animation createAnimation(double xzoom, double yzoom) {
		float toX = (float) (this.getToX() * xzoom);
		float toY = (float) (this.getToY() * yzoom);
		float fromX = (float) (this.getFromX() * xzoom);
		float fromY = (float) (this.getFromY() * yzoom);
		Animation animation = new TranslateAnimation(fromX, toX, fromY, toY);
		dealAnimation(animation);
		return animation;
	}
}