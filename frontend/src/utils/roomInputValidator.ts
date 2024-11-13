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
      return "유효한 깃허브 레포지토리 링크를 입력해주세요.\n예시: https://github.com/username/repository";
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
      return "상호 리뷰 인원은 최소 한 명, 최대 다섯 명 가능해요.";
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

  isAfterTime(value: Date, referenceTime: Date) {
    return value > referenceTime;
  },

  isValidGitHubLink(value: string): boolean {
    const githubRegex = /^https:\/\/github\.com\/[a-zA-Z0-9_-]+\/[a-zA-Z0-9_-]+$/;
    return githubRegex.test(value);
  },
};

export default validators;
