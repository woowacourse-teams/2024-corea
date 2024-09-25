import { theme } from "@/styles/theme";

export const SkeletonShimmer = `
  background: linear-gradient(-225deg, ${theme.COLOR.grey0}, ${theme.COLOR.grey1}, ${theme.COLOR.grey0}, ${theme.COLOR.grey1});
  background-size: 400%;
  animation: skeleton-animation 4s infinite ease-out;
  border-radius: 8px;

  @keyframes skeleton-animation {
    0% {
      background-position: 100% 100%;
    }
    100% {
      background-position: 0% 0%;
    }
  }
`;
