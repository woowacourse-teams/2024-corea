const { server } = require("@/mocks/server");

// 모든 테스트 전에 MSW 서버를 시작합니다.
beforeAll(() => server.listen());

// 각 테스트 후 MSW 핸들러를 리셋합니다.
afterEach(() => server.resetHandlers());

// 모든 테스트 후 MSW 서버를 닫습니다.
afterAll(() => server.close());
