import styled from "styled-components";

export const SearchBarContainer = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  height: 40px;
`;

export const SearchBar = styled.input`
  width: 100%;
  height: 100%;
  padding: 0.6rem 3rem 0.6rem 0.6rem;

  font: ${({ theme }) => theme.TEXT.small};

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 6px;
`;

export const SearchIconWrapper = styled.div`
  cursor: pointer;

  position: absolute;
  top: 50%;
  right: 3px;
  transform: translate(-50%, -50%);
`;
