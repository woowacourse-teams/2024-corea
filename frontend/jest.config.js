export default {
  testEnvironment: "jsdom",
  preset: "ts-jest",
  moduleNameMapper: {
    "^.+\\.svg$": "jest-svg-transformer",
    "\\.(css|less|sass|scss)$": "identity-obj-proxy",
    "^@/(.*)": "<rootDir>/src/$1",
  },
  testEnvironmentOptions: {
    customExportConditions: [""],
  },
  setupFiles: ["./jest.polyfills.js"],
  setupFilesAfterEnv: ["./jest.setup.js"],
};
