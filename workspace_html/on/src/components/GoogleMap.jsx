import { useEffect, useState } from "react";
import { GoogleMap, Marker, InfoWindow, useJsApiLoader } from "@react-google-maps/api";
import { useNavigate } from "react-router-dom";
import '../Styles/GoogleMap.css';

const GOOGLE_API_KEY = import.meta.env.VITE_GEOCODING_API_KEY;
const DEFAULT_CENTER = { lat: 36.80736, lng: 127.1471 };

export default function GoogleMapComponent() {
  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: GOOGLE_API_KEY
  });

  const navigate = useNavigate();

  const [center, setCenter] = useState(DEFAULT_CENTER);
  const [markers, setMarkers] = useState([]);
  const [selected, setSelected] = useState(null);

  const [searchText, setSearchText] = useState("");
  const [searching, setSearching] = useState(false);

  useEffect(() => {
    const helps = JSON.parse(localStorage.getItem("helpRequests") || "[]");
    setMarkers(helps.filter(h => h.lat && h.lng));
    if (helps.length > 0 && helps[0].lat && helps[0].lng) {
      setCenter({ lat: helps[0].lat, lng: helps[0].lng });
    }
  }, []);

  // 검색 (지오코딩)
  const handleSearch = async () => {
    if (!searchText.trim()) return;
    setSearching(true);

    let keyword = searchText.trim();
    if (!/천안|Cheonan/i.test(keyword)) {
      keyword = `천안시 ${keyword}`;
    }

    try {
      const url = `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(
        keyword
      )}&key=${GOOGLE_API_KEY}`;
      const res = await fetch(url);
      const data = await res.json();

      if (data.status === "OK" && data.results[0]) {
        const loc = data.results[0].geometry.location;
        setCenter({ lat: loc.lat, lng: loc.lng });
      } else {
        alert("검색 결과가 없습니다.");
      }
    } catch (err) {
      alert("검색 오류");
    }
    setSearching(false);
  };

  if (!isLoaded)
    return (
      <div style={{ minHeight: 400, textAlign: "center", paddingTop: 50 }}>
        지도를 불러오는 중...
      </div>
    );

  return (
    <div className="googlemap-container">
      <div className="googlemap-searchbar">
        <input
          type="text"
          value={searchText}
          onChange={e => setSearchText(e.target.value)}
          placeholder="천안시 내 동/장소를 입력 (예: 두정동, 불당동)"
          className="googlemap-searchbar-input"
          onKeyDown={e => {
            if (e.key === "Enter") handleSearch();
          }}
        />
        <button
          className="googlemap-searchbar-button"
          onClick={handleSearch}
          disabled={searching}
        >
          검색
        </button>
      </div>

      <GoogleMap
        center={center}
        zoom={15}
        mapContainerStyle={{
          width: "650px",
          maxWidth: "98vw",
          minWidth: "320px",
          height: "400px",
          borderRadius: 16,
        }}
      >
        {markers.map((marker, idx) => (
          <Marker
            key={marker.id || idx}
            position={{ lat: marker.lat, lng: marker.lng }}
            onClick={() => setSelected(marker)}
          />
        ))}
        {selected && (
          <InfoWindow
            position={{ lat: selected.lat, lng: selected.lng }}
            onCloseClick={() => setSelected(null)}
          >
            <div style={{ fontSize: 15, minWidth: 180 }}>
              <b>요청:</b> {selected.request}<br />
              <b>주소:</b> {selected.address}<br />
              <b>시간:</b> {selected.time}<br />
              {selected.problem && (
                <>
                  <b>문제:</b> {selected.problem}<br />
                </>
              )}
              <b>등록일:</b> {new Date(selected.date).toLocaleString()}
              <br /><br />
              <button
                style={{
                  marginTop: 8,
                  padding: "10px 32px",
                  borderRadius: 10,
                  fontWeight: "bold",
                  fontSize: 16,
                  border: "none",
                  background: "#1a73e8",
                  color: "white",
                  cursor: "pointer",
                  boxShadow: "0 1.5px 6px #0001"
                }}
                onClick={() => {
                  navigate('/matching-wait', { state: { selectedHelp: selected } });
                }}
              >
                대기자 페이지로 이동
              </button>
            </div>
          </InfoWindow>
        )}
      </GoogleMap>
    </div>
  );
}