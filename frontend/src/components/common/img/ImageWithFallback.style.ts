import styled, { css } from "styled-components";
import media from "@/styles/media";

export const StyledImg = styled.img<{ $isFallback: boolean }>`
  object-fit: scale-down;

  ${(props) =>
    props.$isFallback &&
    css`
      background-color: ${({ theme }) => theme.COLOR.grey0};
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
