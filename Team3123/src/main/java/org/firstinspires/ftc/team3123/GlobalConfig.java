package org.firstinspires.ftc.team3123;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;

@Config
public class GlobalConfig {
	//Alliance color
	public enum Alliance{
		RED,
		BLUE;

		@NonNull
		@Override
		public String toString() {
			return super.toString();
		}
	}
	public static Alliance alliance = Alliance.BLUE;
}