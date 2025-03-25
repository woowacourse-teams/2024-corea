import { githubAuthUrl } from "@/config/githubAuthUrl";

export const MENU_ITEMS = {
  INTRO: { type: "link", name: "서비스 소개", path: "/intro" },
  GUIDE: { type: "link", name: "가이드", path: "/guide" },
  RANKING: { type: "link", name: "랭킹", path: "/ranking" },
  CONTACT: {
    type: "external",
    name: "문의",
    href: "https://docs.google.com/forms/d/e/1FAIpQLSeuuEJ6oCnRi5AYjlKhWt-H5EEibZPIRLA4lY-5oqZpC2b0SA/viewform",
  },
  PROFILE: { type: "link", name: "마이페이지", path: "/profile" },
  FEEDBACK: { type: "link", name: "피드백 모아보기", path: "/feedback" },
  LOGIN: { type: "external", name: "로그인", href: githubAuthUrl },
  LOGOUT: { type: "action", name: "로그아웃", key: "logout" },
} as const;

export const PUBLIC_MENU_ITEMS = [MENU_ITEMS.INTRO, MENU_ITEMS.GUIDE, MENU_ITEMS.CONTACT] as const;

export const PRIVATE_MENU_ITEMS = [MENU_ITEMS.PROFILE, MENU_ITEMS.FEEDBACK] as const;
