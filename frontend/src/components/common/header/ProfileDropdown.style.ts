import styled from "styled-components";

export const ProfileContainer = styled.div`
  position: relative;
`;

export const DropdownMenu = styled.div<{ show: boolean }>`
  position: absolute;
  z-index: 1;
  right: 0;

  display: ${({ show }) => (show ? "flex" : "none")};
  flex-direction: column;

  width: 200px;
  padding: 1rem;

  background-color: white;
  border-radius: 15px;
  box-shadow: 0 0 7px 1px ${({ theme }) => theme.COLOR.primary2};
`;

export const ProfileWrapper = styled.div`
  display: flex;
  gap: 0.5rem;
`;

export const ProfileInfo = styled.div`
  display: flex;
  flex-direction: column;

  strong {
    font: ${({ theme }) => theme.TEXT.medium};
  }

  span {
    font-size: 0.8rem;
    color: ${({ theme }) => theme.COLOR.grey3};
  }
`;

export const DropdownItemWrapper = styled.ul`
  margin: 0.5rem;
`;

export const DropdownItem = styled.li`
  cursor: pointer;

  display: flex;
  gap: 0.3rem;
  align-items: center;

  padding: 0.5rem;

  font-size: 0.8rem;
  color: ${({ theme }) => theme.COLOR.grey4};

  &:hover {
    background-color: ${({ theme }) => theme.COLOR.grey0};
  }
`;
