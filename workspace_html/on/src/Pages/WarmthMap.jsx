import Layout from '../components/Layout';
import GoogleMapComponent from "../components/GoogleMap.jsx";
import "../Styles/WarmthMap.css"; // 스타일 분리 추천

function WarmthMapPage() {
  return (
    <Layout>
      <main className="main-content warmthmap-main">
        <h2 className="warmthmap-title">우리 동네 온기 지도</h2>
        <GoogleMapComponent />
      </main>
    </Layout>
  );
}

export default WarmthMapPage