import { useEffect, useState, useRef } from 'react';
import '../Styles/WriteModalPage.css';

function WriteModalPage({
  onClose,
  onSubmit,
  initialTitle = '',
  initialContent = '',
  initialImage = ''
}) {
  const [title, setTitle] = useState(initialTitle);
  const [content, setContent] = useState(initialContent);
  const [image, setImage] = useState(initialImage || null);
  const [preview, setPreview] = useState(initialImage || '');
  const inputRef = useRef();

  useEffect(() => {
    setTitle(initialTitle);
    setContent(initialContent);
    setImage(initialImage);
    setPreview(initialImage);
    if (inputRef.current) inputRef.current.focus();
  }, [initialTitle, initialContent, initialImage]);

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onloadend = () => {
      setImage(reader.result);
      setPreview(reader.result);
    };
    reader.readAsDataURL(file);
  };

  const handleImageRemove = () => {
    setImage(null);
    setPreview('');
  };

  // 폼 submit 이벤트
  const handleFormSubmit = (e) => {
    e.preventDefault(); // 새로고침 방지
    if (!title.trim() || !content.trim()) {
      alert('제목과 내용을 모두 입력해주세요.');
      return;
    }
    onSubmit({ title, content, image });
    setTitle('');
    setContent('');
    setImage(null);
    setPreview('');
    onClose();
  };

  return (
    <div className="community-modal-overlay" tabIndex={-1} onClick={onClose}>
      <div
        className="community-modal-box"
        onClick={e => e.stopPropagation()} // 모달 내부 클릭시 닫힘 방지
        role="dialog"
        aria-modal="true"
      >
        <h2>글쓰기</h2>
        <form onSubmit={handleFormSubmit} autoComplete="off">
          <input
            ref={inputRef}
            className="community-modal-input"
            type="text"
            placeholder="제목을 입력하세요"
            value={title}
            onChange={e => setTitle(e.target.value)}
            required
          />
          <textarea
            className="community-modal-textarea"
            placeholder="내용을 입력하세요"
            value={content}
            onChange={e => setContent(e.target.value)}
            required
          />
          {/* 이미지 업로드 */}
          <input
            type="file"
            accept="image/*"
            onChange={handleImageChange}
          />
          {/* 이미지 미리보기 + 삭제 */}
          {preview && (
            <div className="community-image-preview-container">
              <img src={preview} alt="미리보기" className="community-image-preview" />
              <button
                className="community-delete-image-button"
                type="button"
                onClick={handleImageRemove}
              >
                이미지 삭제
              </button>
            </div>
          )}
          <div className="community-modal-buttons">
            <button
              type="submit"
              style={{
                background: "#ffc100",
                color: "#b70820",
                fontWeight: 700,
                minWidth: 90,
                minHeight: 40,   // 버튼 높이 좀 더 크게
                fontSize: "1.12rem",
                border: "1.2px solid #fc0"
              }}
            >
              등록
            </button>
            <button
              type="button"
              onClick={onClose}
              style={{
                marginLeft: 8,
                minWidth: 70,
                minHeight: 40,
                fontSize: "1.08rem"
              }}
            >
              취소
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default WriteModalPage;
