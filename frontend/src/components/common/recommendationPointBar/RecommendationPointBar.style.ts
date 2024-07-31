import styled from "styled-components";

export const BarContainer = styled.div`
  display: flex;
  gap: 10px;
  align-items: center;
  justify-content: center;

  width: 100%;
`;

export const StyledChildren = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  svg {
    width: 20px;
    height: 20px;
    color: ${({ theme }) => theme.COLOR.grey3};
  }
`;
