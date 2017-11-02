package doext.implement;

import org.json.JSONObject;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import core.helper.DoJsonHelper;
import core.helper.DoTextHelper;

public class DoRotateAnim extends DoAnim {
	private float fromDegree;
	private float toDegree;
	private float pivotX;
	private float pivotY;

	public float getFromDegree() {
		return fromDegree;
	}

	public void setFromDegree(float fromDegree) {
		this.fromDegree = fromDegree;
	}

	public float getToDegree() {
		return toDegree;
	}

	public void setToDegree(float toDegree) {
		this.toDegree = toDegree;
	}

	public float getPivotX() {
		return pivotX;
	}

	public void setPivotX(float pivotX) {
		this.pivotX = pivotX;
	}

	public float getPivotY() {
		return pivotY;
	}

	public void setPivotY(float pivotY) {
		this.pivotY = pivotY;
	}

	public void setValuesFromJson(JSONObject data) throws Exception {
		String value = DoJsonHelper.getString(data, "fromDegree", null);
		if (value != null) {
			this.setFromDegree(DoTextHelper.strToFloat(value, 0));
		}
		value = DoJsonHelper.getString(data, "toDegree", null);
		if (value != null) {
			this.setToDegree(DoTextHelper.strToFloat(value, 0));
		}
		value = DoJsonHelper.getString(data, "pivotX", null);
		if (value != null) {
			this.setPivotX(DoTextHelper.strToFloat(value, 0));
		}
		value = DoJsonHelper.getString(data, "pivotY", null);
		if (value != null) {
			this.setPivotY(DoTextHelper.strToFloat(value, 0));
		}
		super.setValuesFromJson(data);
	}

	@Override
	public Animation createAnimation(double xzoom, double yzoom) {
		RotateAnimation animation = new RotateAnimation(this.getFromDegree(), this.getToDegree(), ScaleAnimation.RELATIVE_TO_SELF, this.getPivotX(), ScaleAnimation.RELATIVE_TO_SELF, this.getPivotY());
		dealAnimation(animation);
		return animation;
	}
}