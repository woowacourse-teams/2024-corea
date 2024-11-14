export const DAYS = ["일", "월", "화", "수", "목", "금", "토"] as const;

export const DATE_FORMAT = {
  PATTERN: /(\d{4})-(\d{2})(\d{2})T/,
  REPLACEMENT: "$1-$2-$3T",
} as const;
