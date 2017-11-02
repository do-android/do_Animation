package doext.implement;

import org.json.JSONObject;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import core.helper.DoJsonHelper;
import core.helper.DoTextHelper;

public class DoAnim {
	private String id;
	private int delay;
	private int duration;
	private String curve;
	private int repeatCount = 1;
	private boolean autoReverse;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getCurve() {
		return curve;
	}

	public void setCurve(String curve) {
		this.curve = curve;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public boolean isAutoReverse() {
		return autoReverse;
	}

	public void setAutoReverse(boolean autoReverse) {
		this.autoReverse = autoReverse;
	}

	public void setValuesFromJson(JSONObject data) throws Exception {
		String value = DoJsonHelper.getString(data, "delay", null);
		if (value != null) {
			this.setDelay(DoTextHelper.strToInt(value, 0));
		}
		value = DoJsonHelper.getString(data, "duration", null);
		if (value != null) {
			this.setDuration(DoTextHelper.strToInt(value, 0));
		}
		value = DoJsonHelper.getString(data, "curve", null);
		if (value != null) {
			this.setCurve(value);
		}
		value = DoJsonHelper.getString(data, "repeatCount", null);
		if (value != null) {
			int _repeatCount = DoTextHelper.strToInt(value, 1);
			if (_repeatCount == 0) {
				_repeatCount = 1;
			}
			this.setRepeatCount(_repeatCount);
		}
		value = DoJsonHelper.getString(data, "autoReverse", null);
		if (value != null) {
			this.setAutoReverse(DoTextHelper.strToBool(value, false));
		}
	}

	protected Animation createAnimation(double xzoom, double yzoom) {
		return null;
	}

	public void dealAnimation(Animation animation) {
		animation.setStartOffset(this.getDelay());
		animation.setDuration(this.getDuration());
		Interpolator i = null;
		String curve = this.getCurve();
		if (curve != null && "EaseIn".equals(curve)) { //EaseIn动画启动的时候慢
			i = new AccelerateInterpolator();
		} else if (curve != null && "EaseOut".equals(curve)) { //EaseOut动画结束的时候慢
			i = new DecelerateInterpolator();
		} else if (curve != null && "Linear".equals(curve)) { //Linear动画速度不变
			i = new LinearInterpolator();
		} else { //EaseInOut 动画启动时候慢，中间快，结束的时候慢
			i = new AccelerateDecelerateInterpolator();
		}
		animation.setInterpolator(i);

		int repeatCount = this.getRepeatCount();
		if (this.isAutoReverse()) {
			repeatCount = repeatCount * 2;
			animation.setRepeatMode(Animation.REVERSE);
		} else { //如果设置repeatCount 为默认值 1 与iOS一直执行一次
			animation.setRepeatMode(Animation.RESTART);
		}
		repeatCount--; //减去自身一次
		animation.setRepeatCount(repeatCount);
	}
}
