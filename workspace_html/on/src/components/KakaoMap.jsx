import { Map, MapMarker } from "react-kakao-maps-sdk";

export default function KakaoMap() {
  return (
    <Map
      center={{ lat: 36.80736, lng: 127.1471 }}
      style={{ width: "100%", height: "400px" }}
      level={3}
      // kakaoApiKey={import.meta.env.VITE_KAKAO_MAPS_API_KEY}  <-- 이 줄 삭제!
    >
      <MapMarker position={{ lat: 36.80736, lng: 127.1471 }}>
        <div>여기 중심!</div>
      </MapMarker>
    </Map>
  );
}
