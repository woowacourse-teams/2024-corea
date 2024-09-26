import styled from "styled-components";

export const CreateSection = styled.section`
  display: flex;
  flex-direction: column;

  width: 100%;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};
`;

export const RowContainer = styled.p`
  display: flex;
  gap: 2rem;
  align-items: center;

  width: 100%;
  padding: 2rem;
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
