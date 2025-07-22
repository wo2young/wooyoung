function Book({ title, author, price, imgSrc }) {
    return (
        <div className="book">
            {/* 도서 이미지 출력 (imgSrc는 외부 이미지 URL) */}
            <p><img src={imgSrc} /></p>

            {/* 도서 제목 */}
            <h3>제목: {title}</h3>

            {/* 도서 저자 */}
            <p>저자: {author}</p>

            {/* 도서 가격 */}
            <p>가격: {price}</p>
        </div>
    );
}

export default Book;
