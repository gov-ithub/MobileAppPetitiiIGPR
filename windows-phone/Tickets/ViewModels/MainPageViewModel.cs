using Caliburn.Micro;

namespace Tickets.ViewModels
{
    public class MainPageViewModel : PropertyChangedBase
    {
        private readonly INavigationService _navigationService;

        public MainPageViewModel(INavigationService navigationService)
        {
            _navigationService = navigationService;
        }

        public void SignUp()
        {
            _navigationService.For<SignUpPageViewModel>().Navigate();
        }

        public void SignIn()
        {
            _navigationService.For<SignInPageViewModel>().Navigate();
        }

        public void TicketWithoutAccount()
        {
            _navigationService.For<TicketsWithoutAccountPageViewModel>().Navigate();
        }
    }
}
