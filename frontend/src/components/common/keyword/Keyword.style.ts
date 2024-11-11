import styled from "styled-components";

export const KeywordContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const KeywordLabelContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
`;

export const KeywordButton = styled.button`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  width: fit-content;
  height: fit-content;
  padding: 0.4rem 0.8rem;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey4};
  white-space: nowrap;

  background-color: ${({ theme }) => theme.COLOR.grey0};
  border: 1px solid ${({ theme }) => theme.COLOR.grey0};
  border-radius: 15px;

  svg {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);

    color: ${({ theme }) => theme.COLOR.white};

    opacity: 0;
  }

  &:hover {
    opacity: 0.5;
    background-color: ${({ theme }) => theme.COLOR.grey3};
  }

  &:hover svg {
    opacity: 1;
  }
`;
