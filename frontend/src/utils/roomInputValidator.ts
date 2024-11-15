const validators = {
  title(value: string): string {
    if (this.isStringEmpty(value)) {
      return "제목을 입력해주세요.";
    }
    return "";
  },

  classification(value: string): string {
    if (this.isStringEmpty(value)) {
      return "분류를 선택해주세요.";
    }
    return "";
  },

  content(value: string): string {
    return "";
  },

  repositoryLink(value: string): string {
    if (this.isStringEmpty(value)) {
      return "깃허브 레포지토리 링크를 입력해주세요.";
    }
    if (!this.isValidGitHubLink(value)) {
      if (this.hasGitExtension(value)) {
        return "'.git' 확장자는 제거해주세요.";
      }
      if (!value.startsWith("https://")) {
        return "링크는 'https'로 시작해야 합니다.";
      }
      if (value.includes("?")) {
        return "링크에 쿼리 파라미터를 포함할 수 없습니다.";
      }
      return "유효한 깃허브 레포지토리 링크를 입력해주세요.";
    }
    return "";
  },

  thumbnailLink(value: string): string {
    return "";
  },

  keywords(value: string[]): string {
    return "";
  },

  matchingSize(value: number): string {
    if (this.isNumberEmpty(value)) {
      return "상호 리뷰 인원을 입력해주세요.";
    }
    if (!this.isNumberInRange(value, 1, 5)) {
      return "상호 리뷰 인원은 최소 1명, 최대 5명 가능해요.";
    }
    return "";
  },

  limitedParticipants(value: number, matchingSize: number): string {
    if (this.isNumberEmpty(value)) {
      return "최대 참여 인원을 입력해주세요.";
    }
    if (!this.isGreaterThan(value, matchingSize)) {
      return "최대 참여 인원은 상호 리뷰 인원보다 많아야 해요.";
    }
    return "";
  },

  recruitmentDeadline(value: Date): string {
    if (!this.isAfterTime(value, new Date())) {
      return "모집 마감일은 현재 시간 이후로 설정해주세요.";
    }
    return "";
  },

  reviewDeadline(value: Date, recruitmentDeadline: Date): string {
    if (!this.isAfterTime(value, recruitmentDeadline)) {
      return "코드 리뷰 마감일은 모집 마감일 이후로 설정해주세요.";
    }
    return "";
  },

  // 공통 유효성 함수
  isStringEmpty(value: string): boolean {
    return value === "";
  },

  isNumberEmpty(value: number): boolean {
    return value === 0;
  },

  isNumberInRange(value: number, minRange: number, maxRange: number): boolean {
    return minRange <= value && value <= maxRange;
  },

  isGreaterThan(value: number, minNumber: number): boolean {
    return value > minNumber;
  },

  isValidGitHubLink(value: string): boolean {
    const githubRegex = /^https:\/\/github\.com\/[a-zA-Z0-9_-]+\/[a-zA-Z0-9_-]+\/?$/;
    return githubRegex.test(value);
  },

  hasGitExtension(value: string): boolean {
    return /\.git$/.test(value);
  },

  isAfterTime(value: Date, referenceTime: Date): boolean {
    const valueWithoutSeconds = new Date(
      value.getFullYear(),
      value.getMonth(),
      value.getDate(),
      value.getHours(),
      value.getMinutes(),
    );

    const referenceWithoutSeconds = new Date(
      referenceTime.getFullYear(),
      referenceTime.getMonth(),
      referenceTime.getDate(),
      referenceTime.getHours(),
      referenceTime.getMinutes(),
    );

    return valueWithoutSeconds > referenceWithoutSeconds;
  },
};

export default validators;
