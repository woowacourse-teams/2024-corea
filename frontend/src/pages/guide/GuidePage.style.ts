import styled from "styled-components";
import media from "@/styles/media";

export const GuidPageLayout = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
`;

export const GuideContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
`;

export const CardContainer = styled.div`
  cursor: pointer;

  display: flex;
  gap: 0.4rem;
  align-items: center;
  justify-content: center;

  margin: 0 auto;
  padding: 0.5rem 1rem;

  background-color: ${({ theme }) => theme.COLOR.primary1};
  border: 2px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 12px;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  &:hover {
    ${media.medium`
      transform: scale(1.05);
    `}
    ${media.large`
      transform: scale(1.05);
    `}
  }

  span {
    font: ${({ theme }) => theme.TEXT.small};
  }
`;
