package doext.implement;

import org.json.JSONObject;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import core.helper.DoJsonHelper;
import core.helper.DoTextHelper;

public class DoScaleAnim extends DoAnim {
	private float scaleFromX;
	private float scaleFromY;
	private float scaleToX;
	private float scaleToY;
	private float pivotX;
	private float pivotY;

	public float getScaleFromX() {
		return scaleFromX;
	}

	public void setScaleFromX(float scaleFromX) {
		this.scaleFromX = scaleFromX;
	}

	public float getScaleFromY() {
		return scaleFromY;
	}

	public void setScaleFromY(float scaleFromY) {
		this.scaleFromY = scaleFromY;
	}

	public float getScaleToX() {
		return scaleToX;
	}

	public void setScaleToX(float scaleToX) {
		this.scaleToX = scaleToX;
	}

	public float getScaleToY() {
		return scaleToY;
	}

	public void setScaleToY(float scaleToY) {
		this.scaleToY = scaleToY;
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
		String value = DoJsonHelper.getString(data, "scaleFromX", null);
		if (value != null) {
			this.setScaleFromX(DoTextHelper.strToFloat(value, 0));
		}
		value = DoJsonHelper.getString(data, "scaleFromY", null);
		if (value != null) {
			this.setScaleFromY(DoTextHelper.strToFloat(value, 0));
		}
		value = DoJsonHelper.getString(data, "scaleToX", null);
		if (value != null) {
			this.setScaleToX(DoTextHelper.strToFloat(value, 0));
		}
		value = DoJsonHelper.getString(data, "scaleToY", null);
		if (value != null) {
			this.setScaleToY(DoTextHelper.strToFloat(value, 0));
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
		Animation animation = new ScaleAnimation(this.getScaleFromX(), this.getScaleToX(), this.getScaleFromY(), this.getScaleToY(), ScaleAnimation.RELATIVE_TO_SELF, this.getPivotX(), ScaleAnimation.RELATIVE_TO_SELF, this.getPivotY());
		dealAnimation(animation);
		return animation;
	}
}