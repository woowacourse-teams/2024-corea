import styled from "styled-components";
import media from "@/styles/media";

export const CreateSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 4rem;
  align-items: center;

  width: 100%;
  margin-bottom: 20rem;
  padding: 4rem;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};
`;

export const RowContainer = styled.p`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: flex-start;

  width: 100%;

  ${media.large`
    flex-direction: row;
    align-items: center;
  `}
`;

export const ContentLabel = styled.div`
  flex-shrink: 0;
  width: 250px;
  font: ${({ theme }) => theme.TEXT.medium_bold};

  span {
    font: ${({ theme }) => theme.TEXT.semiSmall};
    color: ${({ theme }) => theme.COLOR.error};
  }
`;

export const ContentInput = styled.div`
  display: flex;
  gap: 1rem;
  width: 100%;
`;
