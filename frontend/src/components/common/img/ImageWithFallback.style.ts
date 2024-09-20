import styled, { css } from "styled-components";
import media from "@/styles/media";

export const ImageWrapper = styled.div<{ $isFallback: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;

  ${(props) =>
    props.$isFallback &&
    css`
      background-color: ${({ theme }) => theme.COLOR.grey0};
    `}
`;

export const StyledImg = styled.img<{ $isFallback: boolean }>`
  width: 100%;
  height: 100%;
  object-fit: scale-down;

  ${(props) =>
    props.$isFallback &&
    css`
      ${media.small`
        padding: 2rem;
      `}

      ${media.medium`
        padding: 2.5rem;
      `}

      ${media.large`
        padding: 3rem;
      `}
    `}
`;
