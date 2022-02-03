package org.firstinspires.ftc.team3736;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;

@Config
public class GlobalConfig {

	//Alliance switch
	public enum Alliance {
		RED,
		BLUE;

		@NonNull
		@Override
		public String toString() {
			return super.toString();
		}
	}

	public static Alliance alliance = Alliance.RED; //Current Alliance

}