import { Link } from "react-router-dom";
import styled from "styled-components";
import { theme } from "@/styles/theme";

export const SkeletonShimmer = `
  background: ${theme.COLOR.grey0};
  background-image: linear-gradient(
    90deg,
    ${theme.COLOR.grey0} 0%,
    ${theme.COLOR.grey1} 50%,
    ${theme.COLOR.grey0} 100%
  );
  background-size: 200% 100%;
  animation: skeleton-shimmer 1.6s ease-in-out infinite;

  @keyframes skeleton-shimmer {
    0% {
      background-position: 100% 0;
    }
    100% {
      background-position: -100% 0;
    }
  }
`;

export const HoverStyledLink = styled(Link)`
  &:hover {
    color: ${theme.COLOR.grey3};
    text-decoration: underline;
    text-underline-offset: 0.3rem;
  }
`;

export const HoverStyledLinkForGuide = styled(Link)`
  &:hover {
    text-decoration: underline;
    text-underline-offset: 0.3rem;
  }
`;

export const VisuallyHidden = `
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border: 0;
`;

export const EllipsisText = `
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  line-height: normal;
`;

export const TYPE_CLASSIFICATION = {
  ALL: "#FFFFFF",
  ANDROID: "#B5D932",
  FRONTEND: "#F7DF1E",
  BACKEND: "#F0A746",
};

export const ErrorText = styled.span`
  min-height: 20px;
  font: ${theme.TEXT.semiSmall};
  color: ${theme.COLOR.error};
  white-space: pre-line;
`;
