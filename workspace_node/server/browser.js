const {chromium} =  require('playwright')

test();
async function test() {
    const broswer = await chromium.launch({
        headless: false
    })    

    const ctx = await broswer.newContext()
    const page = await ctx.newPage()
    await page.goto("https://naver.com")
    
    await page.waitForSelector('#query')
    await page.fill('#query', '삼성전자 주식')
    await page.click('#search-btn')
    // 터미널에 넣어봐 재밌음
    // npx playwright codegen https://naver.com/

    
}