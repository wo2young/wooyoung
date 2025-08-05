import { useNavigate } from 'react-router-dom';
import Layout from '../components/Layout';
import '../Styles/Matching.css';

function MatchingPage() {
  const navigate = useNavigate();

  // 도움받을 분 클릭 시 HelpPage로
  const handleHelpClick = () => {
    navigate('/help');
  };

  // 도움주실 분 클릭 시 MatchingWaitPage로
  const handleWaitClick = () => {
    navigate('/matching-wait');
  };

  return (
    <Layout>
      <div className="container">
        <div className="column">
          <div className="text">
            <h1>도움받을 분은<br />여기 클릭!</h1>
          </div>
          <button className="mbutton Left" onClick={handleHelpClick}>클릭!</button>
        </div>

        <div className="column">
          <div className="text">
            <h1>도움주실 분은<br />여기 클릭!</h1>
          </div>
          <button className="mbutton Right" onClick={handleWaitClick}>클릭!</button>
        </div>
      </div>
    </Layout>
  );
}

export default MatchingPage;
