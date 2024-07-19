import Icon from "../icon/Icon";
import styled from "styled-components";

export const IconButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

export const IconButtonBox = styled.button`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 50px;
  height: 50px;

  background-color: transparent;
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 15px;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  &:active {
    position: relative;
    top: 3px;
    box-shadow: 0 1px 1px rgb(0 0 0 / 10%);
  }
`;

export const StyledIcon = styled(Icon)`
  width: 30px;
  height: 30px;
`;

export const IconButtonText = styled.p`
  font: ${({ theme }) => theme.TEXT.xSmall};
`;
