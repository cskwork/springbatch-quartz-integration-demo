package com.apiBatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UUIDUtil {
	private static final AtomicLong LAST_TIME_MS = new AtomicLong();

	/**
	 * ip, time,
	 * UUID VERSION 1 생성기 (Default 사용)
	 * @return (Custom MAC Address, TimeStamp 조합)
	 */
	public static String type1UUID() { // VARCHAR2(36)
		long most64SigBits = get64MostSignificantBitsForVersion1();
		long least64SigBits = get64LeastSignificantBitsForVersion1();
		return new UUID(most64SigBits, least64SigBits).toString();
	}

	/**
	 * 1 시스템 시간 선언
	 * 2 마지막 저장 시간이 현재 시간 보다 크거나 같으면 : 새로운 시간 +1로 생성
	 * 3 마지막 저장 시간과 현재 시간이 같으면 값을 그대로 반환, 현재 시간이 변경됐으면 다음 루프로 실행.
	 */
	public static long generateTimeBasedUUID() {
		long now = System.currentTimeMillis();
		int count = 0;
		while (count < 100000) {
			long lastTime = LAST_TIME_MS.get();
			if (lastTime >= now) // 마지막으로 선언된 시스템 시간
				now = lastTime + 1;
			if (LAST_TIME_MS.compareAndSet(lastTime, now)) // 현재 시간과 일치하는 값이면 return
				return now;
			count++;
		}
		return now + 1;
	}

	/**
	 * [MAC Address] MAC 주소 대신에 임의의 48비트 숫자를 생성합니다.(보안 우려로 대체)
	 * @return
	 */
	private static long get64LeastSignificantBitsForVersion1() {
		Random random = new Random();
		long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
		long variant3BitFlag = 0x8000000000000000L;
		return random63BitLong | variant3BitFlag;
	}

	/*
	 * [TimeStamp] 타임스템프를 이용하여 64개의 최상위 비트를 생성.
	 */
	private static long get64MostSignificantBitsForVersion1() {
		final long currentTimeMillis = System.currentTimeMillis();
		double randomNumb = Math.random() * 100;
		final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
		final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
		final long version = 1 << 12;
		final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
		return time_low | time_mid | version | time_hi;
	}

	/**
	 * UUID v1을 생성 후 반환.(Custom MAC Address, TimeStamp 조합)
	 * @return
	 */
	public static UUID generateType1UUID() {
		long most64SigBits = get64MostSignificantBitsForVersion1();
		long least64SigBits = get64LeastSignificantBitsForVersion1();
		return new UUID(most64SigBits, least64SigBits);
	}
}
