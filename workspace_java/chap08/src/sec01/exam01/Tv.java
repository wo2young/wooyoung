package sec01.exam01;

public class Tv  implements RemoteControl, OTT {
	
	int vol;
	
	@Override
	public void turnOn() {
		// TODO Auto-generated method stub
		System.out.println("tv를 킵니다");
	}

	@Override
	public void turnOff() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setVOlume(int vol) {
		if(vol > RemoteControl.MAX_VOLUME) {
			System.out.println(RemoteControl.MAX_VOLUME + "이하에 숫자만 넣어주세요");
			this.vol = RemoteControl.MAX_VOLUME; 
		}
		else if(vol < RemoteControl.MIN_VOLUME) {
			System.out.println(RemoteControl.MIN_VOLUME + "이상에 숫자만 넣어주세요");
			this.vol = RemoteControl.MIN_VOLUME;
		} else {
			this.vol = vol;
		}
	}

	@Override
	public void netflix() {
		// TODO Auto-generated method stub
		System.out.println("넷플릭스 시청");
	}
}
